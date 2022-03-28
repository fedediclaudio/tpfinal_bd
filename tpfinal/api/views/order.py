import logging
import datetime
import random

from django.shortcuts import render
from django.http.response import JsonResponse
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import JSONParser
from rest_framework import status

from api.models.order import Order
from api.models.product import Product
from api.models.delivery_man import DeliveryMan

from api.views.generic import GenericViewList, GenericViewDetail
from api.serializers import OrderSerializer, RatingSerializer

logger = logging.getLogger()

# Create your views here.
class OrderViewList(GenericViewList):
    model = Order
    serializer = OrderSerializer

class OrderViewDetail(APIView):
    def get(self, request, number, format=None, *args, **kwargs):
        # Get details of an existing order
        try:
            order = Order.objects.get(number=number)
        except Order.DoesNotExist:
            return Response({"status": "error", "message": f"Order number {number} not found"}, status=status.HTTP_404_NOT_FOUND)
        serializer = OrderSerializer(order)
        return Response(serializer.data, status=status.HTTP_200_OK)

    # PUT is not supported, we do not want users to directly modify orders like this

    def delete(self, request, number, format=None, *args, **kwargs):
        # Delete an existing order
        try:
            order = Order.objects.get(number=number)
        except Order.DoesNotExist:
            return Response({"status": "error", "message": f"Order number {number} not found"}, status=status.HTTP_404_NOT_FOUND)
        order.delete()
        return Response({"status": "success", "message": f"Order number {number} deleted"}, status=status.HTTP_204_NO_CONTENT)


class OrderMostExpensive(APIView):
    date_format = "%Y-%m-%d"

    def get(self, request, format=None, *args, **kwargs):
        request_params = request.query_params
        request_date = request_params.get('date', None)
        try:
            target_date = datetime.datetime.strptime(request_date, self.date_format)
        except ValueError:
            return Response({"status": "error", "message": f"Date {request_date} does not have 'YYYY-MM-DD' format"}, status=status.HTTP_400_BAD_REQUEST)
        all_orders_for_date = Order.objects.filter(date_of_order__date=target_date).order_by('-total_price')
        try:
            order = all_orders_for_date[0]
        except IndexError:
            return Response({"status": "success", "message": f"No orders found for day {request_date}"}, status=status.HTTP_204_NO_CONTENT)
        serializer = OrderSerializer(order)
        return Response(serializer.data, status=status.HTTP_200_OK)


class OrderAddItem(APIView):
    def post(self, request, number, format=None, *args, **kwargs):
        data = JSONParser().parse(request)
        try:
            order = Order.objects.get(number=number)
        except Order.DoesNotExist:
            return Response({"status": "error", "message": f"Order number {number} not found"}, status=status.HTTP_404_NOT_FOUND)
        try:
            product_id = data["product"]
            quantity = data["quantity"]
            description = data.get("description", "")
        except KeyError as e:
            return Response({"status": "error", "message": f"Parameter {str(e)} missing on request body"}, status=status.HTTP_400_BAD_REQUEST)
        try:
            order.add_item(product_id, quantity, description)
        except RuntimeError:
            return Response({"status": "error", "message": f"Cannot add items to order number {number}"}, status=status.HTTP_409_CONFLICT) 
        return Response({"status": "success", "message": f"Added item to order number {number}"}, status=status.HTTP_200_OK)


class OrderAddRating(APIView):
    def post(self, request, number, format=None, *args, **kwargs):
        try:
            order = Order.objects.get(number=number)
        except Order.DoesNotExist:
            return Response({"status": "error", "message": f"Order number {number} not found"}, status=status.HTTP_404_NOT_FOUND)
        if not order.can_rate():
            return Response({"status": "error", "message": f"Cannot rate order number {number}"}, status=status.HTTP_409_CONFLICT) 
        data = JSONParser().parse(request)
        data["order"] = number
        serializer = RatingSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return Response({"status": "success", "data": serializer.data}, status=status.HTTP_201_CREATED)
        else:
            return Response({"status": "error", "data": serializer.errors}, status=status.HTTP_400_BAD_REQUEST)


## This class handles all the status logic
class OrderViewModifyStatus(APIView):
    def __assign(self, order):
        available_delivery_men = DeliveryMan.objects.filter(free=True)
        if len(available_delivery_men) == 0:
            return Response({"status": "error", "message": f"No delivery man is available"}, status=status.HTTP_409_CONFLICT)
        delivery_man = random.choice(available_delivery_men)
        try:
            order.assign(delivery_man)
        except Exception:
            logger.exception("Order cannot be assigned")
            return Response({"status": "error", "message": f"Order cannot be assigned"}, status=status.HTTP_400_BAD_REQUEST)
        return Response({"status": "success", "message": f"Order assigned"}, status=status.HTTP_200_OK)

    def __cancel(self, order):
        try:
            order.cancel()
        except Exception:
            logger.exception("Order cannot be canceled")
            return Response({"status": "error", "message": f"Order cannot be cancelled"}, status=status.HTTP_400_BAD_REQUEST)
        return Response({"status": "success", "message": f"Order cancelled"}, status=status.HTTP_200_OK)

    def __refuse(self, order):
        try:
            order.refuse()
        except Exception:
            logger.exception("Order cannot be refused")
            return Response({"status": "error", "message": f"Order cannot be refused"}, status=status.HTTP_400_BAD_REQUEST)
        return Response({"status": "success", "message": f"Order refused"}, status=status.HTTP_200_OK)

    def __deliver(self, order):
        try:
            order.deliver()
        except Exception:
            logger.exception("Order cannot be delivered")
            return Response({"status": "error", "message": f"Order cannot be delivered"}, status=status.HTTP_400_BAD_REQUEST)
        return Response({"status": "success", "message": f"Order delivered"}, status=status.HTTP_200_OK)

    def __send(self, order):
        try:
            order.send()
        except Exception:
            logger.exception("Order cannot be sent")
            return Response({"status": "error", "message": f"Order cannot be sent"}, status=status.HTTP_400_BAD_REQUEST)
        return Response({"status": "success", "message": f"Order sent"}, status=status.HTTP_200_OK)

    def put(self, request, number, stat_update, format=None, *args, **kwargs):
        # Get details of an existing order
        try:
            order = Order.objects.get(number=number)
        except Order.DoesNotExist:
            return Response({"status": "error", "message": f"Order number {number} not found"}, status=status.HTTP_404_NOT_FOUND)
        function_to_apply = {
            "assign": self.__assign,
            "cancel": self.__cancel,
            "refuse": self.__refuse,
            "send": self.__send,
            "deliver": self.__deliver,
        }
        res = function_to_apply[stat_update](order)
        return res
