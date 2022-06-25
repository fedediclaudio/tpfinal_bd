import datetime
from math import prod

from djongo import models
from .client import Client
from .address import Address
from .delivery_man import DeliveryMan
from .order_status import OrderStatus, OrderStatusValues

from bson import ObjectId

class Order(models.Model):
    _id = models.ObjectIdField(primary_key=True)
    date_of_order = models.DateTimeField(auto_now_add=True)
    comments = models.CharField(max_length=1000, null=True)
    total_price = models.FloatField(default=0.0)

    # dependencies with other models
    delivery_address = models.ForeignKey(Address, on_delete=models.PROTECT)
    delivery_man = models.ForeignKey(DeliveryMan, on_delete=models.PROTECT, null=True)
    client = models.ForeignKey(Client, on_delete=models.PROTECT)
    status = models.OneToOneField(OrderStatus, on_delete=models.PROTECT)

    def save(self, *args, **kwargs) -> None:
        if not self.pk:
            self.status = OrderStatus.objects.create()
        super().save(*args, **kwargs)

    def add_item(self, product_id, quantity: int, description: str) -> None:
        # avoid circular import
        from .item import Item
        from .product import Product

        if not self.status.can_add_item():
            raise RuntimeError("Cannot add item to this order")
        product = Product.objects.get(_id=ObjectId(product_id))
        item = Item(quantity=quantity, description=description, product=product,
                    order=self)
        item.save()

    def assign(self, delivery_man: DeliveryMan) -> None:
        self.status.assign()
        self.delivery_man = delivery_man
        self.save()

    def refuse(self) -> None:
        self.status.refuse()
        self.delivery_man.cancelled_order()

    def send(self) -> None:
        self.status.send()
        self.delivery_man.occupy()

    def cancel(self) -> None:
        if self.status.is_assigned():
            self.client.cancelled_order()
        self.status.cancel()

    def deliver(self) -> None:
        self.status.deliver()
        self.delivery_man.delivered_order()
        self.client.received_order()

    def get_suppliers(self):
        suppliers = []
        # Pre-loads items and products to avoid hitting the DB multiple times
        items = self.items.select_related('product')
        for item in items:
            suppliers.append(item.product.supplier)
        return suppliers

    def can_rate(self):
        try:
            rating = self.rating
        except Exception:
            pass
        else:
            return False
        return (self.status.is_delivered())
