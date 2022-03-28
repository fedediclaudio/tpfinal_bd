from django.db import models
from .supplier_type import SupplierType
from django.db.models import Avg

class Supplier(models.Model):
    name = models.CharField(max_length=200)
    cuil = models.CharField(max_length=200)
    address = models.CharField(max_length=200)
    coordX = models.FloatField()
    coordY = models.FloatField()
    rating = models.FloatField(null=True)

    # dependencies with other models
    type = models.ManyToManyField(SupplierType, blank=True)

    def update_supplier_rating(self):
        # avoid circular import
        from .rating import Rating

        avg_ratings = Rating.objects.filter(order__items__product__supplier=self).aggregate(Avg('score'))
        rating = avg_ratings["score__avg"]
        if rating is not None:
            self.rating = rating
            self.save()
