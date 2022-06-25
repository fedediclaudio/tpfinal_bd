from curses import KEY_A1
from djongo import models
from .product import Product
from .order import Order

class Item(models.Model):
    _id = models.ObjectIdField(primary_key=True)
    quantity = models.IntegerField()
    description = models.CharField(max_length=200)

    # dependencies with other models
    product = models.ForeignKey(Product, on_delete=models.PROTECT, related_name='items')
    order = models.ForeignKey(Order, on_delete=models.PROTECT, related_name='items')
