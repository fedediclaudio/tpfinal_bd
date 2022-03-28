import logging

from django.shortcuts import render
from django.http.response import JsonResponse
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import JSONParser
from rest_framework import status

logger = logging.getLogger()

# Create your views here.
class GenericViewList(APIView):
    model = None
    serializer = None

    def get(self, request, format=None, *args, **kwargs):
        # Get details of all products
        products = self.model.objects.all()
        serializer = self.serializer(products, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def post(self, request, format=None, *args, **kwargs):
        # Create a new product
        serializer = self.serializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({"status": "success", "data": serializer.data}, status=status.HTTP_201_CREATED)
        else:
            return Response({"status": "error", "data": serializer.errors}, status=status.HTTP_400_BAD_REQUEST)

class GenericViewDetail(APIView):
    model = None
    serializer = None

    def get(self, request, number, format=None, *args, **kwargs):
        # Get details of an existing product
        try:
            product = self.model.objects.get(id=number)
        except self.model.DoesNotExist:
            return Response({"status": "error", "message": f"ID number {number} not found"}, status=status.HTTP_404_NOT_FOUND)
        serializer = self.serializer(product)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def put(self, request, number, format=None, *args, **kwargs):
        # Update an existing product
        data = JSONParser().parse(request)
        try:
            product = self.model.objects.get(id=number)
        except self.model.DoesNotExist:
            return Response({"status": "error", "message": f"ID number {number} not found"}, status=status.HTTP_404_NOT_FOUND)
        serializer = self.serializer(product, data=data)
        if number is None:
            return Response({"status": "error", "message": "No ID number provided"}, status=status.HTTP_400_BAD_REQUEST)
        if serializer.is_valid():
            serializer.save()
            return Response({"status": "success", "data": serializer.data}, status=status.HTTP_200_OK)
        else:
            return Response({"status": "error", "data": serializer.errors}, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, number, format=None, *args, **kwargs):
        # Delete an existing product
        try:
            product = self.model.objects.get(id=number)
        except self.serializer.DoesNotExist:
            return Response({"status": "error", "message": f"ID number {number} not found"}, status=status.HTTP_404_NOT_FOUND)
        product.delete()
        return Response({"status": "success", "message": f"ID number {number} deleted"}, status=status.HTTP_204_NO_CONTENT)
