from django.core.exceptions import ValidationError
from django.db import models
from .order import Order

class Rating(models.Model):
    @staticmethod
    def score_validator(value):
        if value not in range(1,5+1):
            raise ValidationError(f"{value} is not a valid rating value")

    score = models.IntegerField(validators=[score_validator.__func__])
    commentary = models.CharField(max_length=200, null=True)

    order = models.OneToOneField(Order, related_name='rating', on_delete=models.PROTECT)

    def save(self, *args, **kwargs):
        super().save(*args, **kwargs)
        # Trigger rating update on suppliers related to the order
        suppliers = self.order.get_suppliers()
        for supplier in suppliers:
            supplier.update_supplier_rating()
