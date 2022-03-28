import logging

from django.shortcuts import render
from django.http.response import JsonResponse
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import JSONParser
from rest_framework import status

from api.views.generic import GenericViewList, GenericViewDetail
from api.models.product_type import ProductType

from api.serializers import ProductTypeSerializer

logger = logging.getLogger()

# Create your views here.
class ProductTypeViewList(GenericViewList):
    model = ProductType
    serializer = ProductTypeSerializer

class ProductTypeViewDetail(GenericViewDetail):
    model = ProductType
    serializer = ProductTypeSerializer
