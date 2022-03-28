from rest_framework import serializers

from .models.product import Product
from .models.product_type import ProductType
from .models.historical_product_price import HistoricalProductPrice
from .models.supplier import Supplier
from .models.supplier_type import SupplierType
from .models.address import Address
from .models.client import Client
from .models.delivery_man import DeliveryMan
from .models.order import Order
from .models.order_status import OrderStatus
from .models.item import Item
from .models.rating import Rating

class AddressSerializer(serializers.ModelSerializer):
    class Meta:
        model = Address
        fields = ('__all__')

class ClientSerializer(serializers.ModelSerializer):
    class Meta:
        model = Client
        fields = ('__all__')
        read_only_fields = ['active', 'score', 'date_of_admission']

class DeliveryManSerializer(serializers.ModelSerializer):
    class Meta:
        model = DeliveryMan
        fields = ('__all__')
        read_only_fields = ['active', 'score', 'number_of_success', 'free', 'date_of_admission']

class ItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = Item
        fields = ('__all__')

class RatingSerializer(serializers.ModelSerializer):
    class Meta:
        model = Rating
        fields = ('__all__')

class OrderStatusSerializer(serializers.ModelSerializer):
    current_status = serializers.SerializerMethodField()

    def get_current_status(self, obj):
        return obj.get_status_display()

    class Meta:
        model = OrderStatus
        fields = ('current_status', 'start_date')


class OrderSerializer(serializers.ModelSerializer):
    items = ItemSerializer(many=True, read_only=True)
    rating = RatingSerializer(read_only=True)
    status = OrderStatusSerializer(read_only=True)
    class Meta:
        model = Order
        fields = ('__all__')
        read_only_fields = ['items', 'rating', 'status']


class ProductSerializer(serializers.ModelSerializer):
    class Meta:
        model = Product
        fields = ('__all__')

class ProductPriceAvgSerializer(serializers.Serializer):
    type = serializers.IntegerField(read_only=True)
    avg_price = serializers.DecimalField(max_digits=None, decimal_places=2)

class ProductTypeSerializer(serializers.ModelSerializer):
    class Meta:
        model = ProductType
        fields = ('__all__')

class ProductPriceHistorySerializer(serializers.ModelSerializer):
    class Meta:
        model = HistoricalProductPrice
        fields = ('price', 'start_date', 'product')

class SupplierSerializer(serializers.ModelSerializer):
    class Meta:
        model = Supplier
        fields = ('__all__')

# class SupplierAllTypesSerializer(serializers.Serializer):
#     supplier = serializers.IntegerField(read_only=True)

class SupplierLowestRatingSerializer(serializers.ModelSerializer):
    lowest_rating_count = serializers.IntegerField(read_only=True)
    class Meta:
        model = Supplier
        fields = ['id', 'lowest_rating_count']

class SupplierTypeSerializer(serializers.ModelSerializer):
    class Meta:
        model = SupplierType
        fields = ('__all__')
