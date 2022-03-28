import logging

from django.shortcuts import render
from django.http.response import JsonResponse
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import JSONParser
from rest_framework import status

from api.views.generic import GenericViewList, GenericViewDetail
from api.models.supplier_type import SupplierType

from api.serializers import SupplierTypeSerializer

logger = logging.getLogger()

# Create your views here.
class SupplierTypeViewList(GenericViewList):
    model = SupplierType
    serializer = SupplierTypeSerializer

class SupplierTypeViewDetail(GenericViewDetail):
    model = SupplierType
    serializer = SupplierTypeSerializer
