import logging

from django.db.models import Count, Q, Sum
from django.shortcuts import render
from django.http.response import JsonResponse
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import JSONParser
from rest_framework import status

from api.models.order import Order
from api.models.supplier import Supplier
from api.models.product import Product
from api.models.product_type import ProductType

from ..models import DeliveryMan
from api.serializers import (
    ProductSerializer,
    SupplierSerializer,
    # SupplierAllTypesSerializer,
    SupplierLowestRatingSerializer,
    OrderSerializer
)
from api.views.generic import GenericViewList, GenericViewDetail

logger = logging.getLogger()

# Create your views here.
class SupplierViewList(GenericViewList):
    model = Supplier
    serializer = SupplierSerializer

class SupplierViewDetail(GenericViewDetail):
    model = Supplier
    serializer = SupplierSerializer

class SupplierViewSearchByType(APIView):
    def get(self, request, format=None, *args, **kwargs):
        request_params = request.query_params
        supplier_type = request_params.get('type', None)
        if supplier_type is None:
            return Response({"status": "error", "message": "This endpoint requires a supplier type ID as part of the URL"}, status=status.HTTP_400_BAD_REQUEST)
        suppliers = Supplier.objects.filter(type__id=supplier_type)
        serializer = SupplierSerializer(suppliers, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

class SupplierViewProducts(APIView):
    def get(self, request, supplier_id, format=None, *args, **kwargs):
        try:
            supplier = Supplier.objects.get(id=supplier_id)
        except Supplier.DoesNotExist:
            return Response({"status": "error", "message": f"Supplier ID {supplier_id} not found"}, status=status.HTTP_404_NOT_FOUND)
        products = Product.objects.filter(supplier__id=supplier_id)
        serializer = ProductSerializer(products, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

class SupplierViewAllTypes(APIView):
    def get(self, request, format=None, *args, **kwargs):
        total_type_count = ProductType.objects.values('id').count()
        suppliers = Product.objects.values('supplier').annotate(type_count=Count('type', distinct=True)).filter(type_count=total_type_count)
        supplier_ids = [res["supplier"] for res in suppliers]
        return Response(supplier_ids, status=status.HTTP_200_OK)
        # serializer = SupplierAllTypesSerializer(suppliers, many=True)
        # return Response(serializer.data, status=status.HTTP_200_OK)

class SupplierViewAllWithLowestRating(APIView):
    def get(self, request, format=None, *args, **kwargs):
        suppliers = Supplier.objects.\
            annotate(lowest_rating_count=Count('products__items__order__rating',
                                               distinct=True,
                                               filter=Q(products__items__order__rating__score=1))).\
            filter(lowest_rating_count__gte=1)
        serializer = SupplierLowestRatingSerializer(suppliers, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

class SupplierViewAllMostOrders(APIView):
    def get(self, request, format=None, *args, **kwargs):
        all_suppliers = Supplier.objects.annotate(order_count=Count('products__items__order', distinct=True)).order_by('-order_count')
        suppliers = all_suppliers[:10]
        serializer = SupplierSerializer(suppliers, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

class SupplierViewOrderWithMostProducts(APIView):
    def get(self, request, supplier_id, format=None, *args, **kwargs):
        try:
            supplier = Supplier.objects.get(id=supplier_id)
        except Supplier.DoesNotExist:
            return Response({"status": "error", "message": f"Supplier ID {supplier_id} not found"}, status=status.HTTP_404_NOT_FOUND)
        orders = Order.objects.annotate(item_count=Sum('items__quantity')).filter(items__product__supplier=supplier_id).order_by('-item_count')
        # We use only the top 5 orders
        most_p_orders = orders[:5]
        serializer = OrderSerializer(most_p_orders, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

