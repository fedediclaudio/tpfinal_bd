import logging

from django.shortcuts import render
from django.http.response import JsonResponse
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import JSONParser
from rest_framework import status

from api.views.generic import GenericViewList, GenericViewDetail
from api.models.client import Client

from api.serializers import ClientSerializer

logger = logging.getLogger()

# Create your views here.
class ClientViewList(GenericViewList):
    model = Client
    serializer = ClientSerializer

class ClientViewDetail(GenericViewDetail):
    model = Client
    serializer = ClientSerializer
