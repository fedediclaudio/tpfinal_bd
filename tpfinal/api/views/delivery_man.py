import logging

from django.shortcuts import render
from django.http.response import JsonResponse
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import JSONParser
from rest_framework import status

from api.views.generic import GenericViewList, GenericViewDetail
from api.models.delivery_man import DeliveryMan

from api.serializers import DeliveryManSerializer

logger = logging.getLogger()

# Create your views here.
class DeliveryManViewList(GenericViewList):
    model = DeliveryMan
    serializer = DeliveryManSerializer

class DeliveryManViewDetail(GenericViewDetail):
    model = DeliveryMan
    serializer = DeliveryManSerializer

class DeliveryManViewHighestRated(APIView):
    def get(self, request, format=None, *args, **kwargs):
        all_delivery_men = DeliveryMan.objects.all().order_by('-score')
        data = all_delivery_men[:10]
        serializer = DeliveryManSerializer(data, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)
