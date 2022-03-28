import logging

from django.shortcuts import render
from django.http.response import JsonResponse
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import JSONParser
from rest_framework import status

from api.views.generic import GenericViewList, GenericViewDetail
from api.models.address import Address

from api.serializers import AddressSerializer

logger = logging.getLogger()

# Create your views here.
class AddressViewList(GenericViewList):
    model = Address
    serializer = AddressSerializer

class AddressViewDetail(GenericViewDetail):
    model = Address
    serializer = AddressSerializer
