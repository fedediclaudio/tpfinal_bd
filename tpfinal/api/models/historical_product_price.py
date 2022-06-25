import django.utils.timezone as timezone
from djongo import models
from .product import Product

class HistoricalProductPrice(models.Model):
    _id = models.ObjectIdField(primary_key=True)
    price = models.FloatField()
    start_date = models.DateTimeField(default=timezone.now)

    # dependencies with other models
    product = models.ForeignKey(Product, on_delete=models.PROTECT, related_name='historic_prices')
