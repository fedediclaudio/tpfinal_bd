import django.utils.timezone as timezone
from django.db import models, transaction

from .supplier import Supplier
from .product_type import ProductType

class Product(models.Model):
    name = models.CharField(max_length=200)
    price = models.FloatField()
    weight = models.FloatField()
    description = models.CharField(max_length=200)

    # dependencies with other models
    supplier = models.ForeignKey(Supplier, on_delete=models.PROTECT, related_name='products')
    type = models.ManyToManyField(ProductType)

    @transaction.atomic
    def save(self, *args, **kwargs):
        # avoid circular import
        from .historical_product_price import HistoricalProductPrice

        super().save(*args, **kwargs)
        if self.pk is not None:
            orig = Product.objects.get(pk=self.pk)
            if orig.price != self.price:
                # Price wasn't modified, we are done here
                return
        HistoricalProductPrice.objects.create(
            price=self.price,
            product=self
        )
