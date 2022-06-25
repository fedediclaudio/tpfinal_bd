from rest_framework.serializers import (
    Serializer,
    ModelSerializer,
    Field,
    ListField,
    PrimaryKeyRelatedField,
    IntegerField,
    DecimalField,
    SerializerMethodField,
    ValidationError
)

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
from bson.objectid import ObjectId
from bson.errors import InvalidId
from django.utils.encoding import smart_text

class ObjectIdField(Field):
    """ Serializer field for Djongo ObjectID fields """
    def to_internal_value(self, data):
        # Serialized value -> Database value
        try:
            return ObjectId(str(data))  # Get the ID, then build an ObjectID instance using it
        except InvalidId:
            raise ValidationError(
                '`{}` is not a valid ObjectID'.format(data)
            )

    def to_representation(self, value):
        # Database value -> Serialized value
        if not ObjectId.is_valid(value):  # User submitted ID's might not be properly structured
            raise InvalidId
        return smart_text(value)

class MongoTableSerializer(ModelSerializer):
    _id = ObjectIdField(read_only=True)

class AddressSerializer(MongoTableSerializer):
    client = PrimaryKeyRelatedField(queryset=Client.objects.all(), pk_field=ObjectIdField())
    class Meta:
        model = Address
        fields = ('__all__')

class ClientSerializer(MongoTableSerializer):
    class Meta:
        model = Client
        fields = ('__all__')
        read_only_fields = ['active', 'score', 'date_of_admission']

class DeliveryManSerializer(MongoTableSerializer):
    class Meta:
        model = DeliveryMan
        fields = ('__all__')
        read_only_fields = ['active', 'score', 'number_of_success', 'free', 'date_of_admission']

class ItemSerializer(MongoTableSerializer):
    product = PrimaryKeyRelatedField(queryset=Product.objects.all(), pk_field=ObjectIdField())
    order = PrimaryKeyRelatedField(queryset=Order.objects.all(), pk_field=ObjectIdField())

    class Meta:
        model = Item
        fields = ('__all__')

class RatingSerializer(MongoTableSerializer):
    order = PrimaryKeyRelatedField(queryset=Order.objects.all(), pk_field=ObjectIdField())
    class Meta:
        model = Rating
        fields = ('__all__')

class OrderStatusSerializer(MongoTableSerializer):
    current_status = SerializerMethodField()

    def get_current_status(self, obj):
        return obj.get_status_display()

    class Meta:
        model = OrderStatus
        fields = ('current_status', 'start_date')


class OrderSerializer(MongoTableSerializer):
    items = ItemSerializer(many=True, read_only=True)
    rating = RatingSerializer(read_only=True)
    status = OrderStatusSerializer(read_only=True)
    delivery_address = PrimaryKeyRelatedField(queryset=Address.objects.all(), pk_field=ObjectIdField())
    delivery_man = PrimaryKeyRelatedField(queryset=DeliveryMan.objects.all(), pk_field=ObjectIdField(), required=False)
    client = PrimaryKeyRelatedField(queryset=Client.objects.all(), pk_field=ObjectIdField())
    
    class Meta:
        model = Order
        fields = ('__all__')
        read_only_fields = ['items', 'rating', 'status']

# class TypeSerializer(Serializer):
#     type = ObjectIdField(read_only=True)

class ProductSerializer(MongoTableSerializer):
    supplier = PrimaryKeyRelatedField(queryset=Supplier.objects.all(), pk_field=ObjectIdField())
    type = PrimaryKeyRelatedField(queryset=ProductType.objects.all(), pk_field=ObjectIdField(), many=True)
    class Meta:
        model = Product
        fields = ('__all__')

class ProductPriceAvgSerializer(Serializer):
    type = ObjectIdField(read_only=True)
    avg_price = DecimalField(max_digits=None, decimal_places=2)

class ProductTypeSerializer(MongoTableSerializer):
    class Meta:
        model = ProductType
        fields = ('__all__')

class ProductPriceHistorySerializer(MongoTableSerializer):
    product = PrimaryKeyRelatedField(queryset=Product.objects.all(), pk_field=ObjectIdField())
    class Meta:
        model = HistoricalProductPrice
        fields = ('price', 'start_date', 'product')

class SupplierSerializer(MongoTableSerializer):
    type = PrimaryKeyRelatedField(queryset=SupplierType.objects.all(), pk_field=ObjectIdField(), many=True)
    class Meta:
        model = Supplier
        fields = ('__all__')

class SupplierLowestRatingSerializer(Serializer):
    _id = ObjectIdField(read_only=True)
    lowest_rating_count = IntegerField(read_only=True)

class SupplierTypeSerializer(MongoTableSerializer):
    class Meta:
        model = SupplierType
        fields = ('__all__')
