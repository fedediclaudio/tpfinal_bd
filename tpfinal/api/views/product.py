from datetime import datetime
import logging
from re import S

from django.db.models import Avg
from django.shortcuts import render
from django.http.response import JsonResponse
from django.utils import timezone
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import JSONParser
from rest_framework import status

from api.models.product import Product

from api.views.generic import GenericViewList, GenericViewDetail
from api.serializers import ProductSerializer, ProductPriceAvgSerializer, ProductPriceHistorySerializer

logger = logging.getLogger()

# Create your views here.
class ProductViewList(GenericViewList):
    model = Product
    serializer = ProductSerializer

class ProductViewDetail(GenericViewDetail):
    model = Product
    serializer = ProductSerializer

class ProductViewAverage(APIView):
    def get(self, request, format=None, *args, **kwargs):
        # Get details of all products
        products = Product.objects.values('type').annotate(avg_price=Avg('price'))
        serializer = ProductPriceAvgSerializer(products, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

class ProductViewHistoricValues(APIView):
    date_format = "%Y-%m-%d"

    def get(self, request, number, format=None, *args, **kwargs):
        request_params = request.query_params
        start_date_str = request_params.get('start_date', None)
        end_date_str = request_params.get('end_date', None)
        now = timezone.make_aware(datetime.utcnow())
        if start_date_str is None:
            start_date = now
        else:
            start_date = datetime.strptime(start_date_str, self.date_format)
            start_date = timezone.make_aware(start_date)
        if end_date_str is None:
            end_date = now
        else:
            end_date = datetime.strptime(end_date_str, self.date_format)
            end_date = timezone.make_aware(end_date)
        # Get details of all products
        products = Product.objects.get(id=number)
        historic_prices = products.historic_prices.all().order_by('start_date')
        start_index = None
        end_index = None
        for index in range(len(historic_prices)):
            historic_price = historic_prices[index]
            logger.info(historic_price.start_date)
            if historic_price.start_date >= end_date:
                if start_index is None:
                    msg = {"status": "success",
                           "message": "No historic data is available for this timeframe"}
                    return Response(msg, status=status.HTTP_204_NO_CONTENT)
                else:
                    end_index = index
                    break
            if start_index is None and historic_price.start_date >= start_date:
                start_index = index
        if end_index is None:
            end_index = len(historic_prices)
        if start_index is None:
            msg = {"status": "success",
                    "message": "No historic data is available for this timeframe"}
            return Response(msg, status=status.HTTP_204_NO_CONTENT)
        product_prices = historic_prices[start_index:end_index]
        serializer = ProductPriceHistorySerializer(product_prices, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

