from email.policy import default
from django.db import models
from django.forms import ValidationError
from .delivery_man import DeliveryMan

# Internal map for status
class OrderStatusValues(models.IntegerChoices):
    PENDING = 0, 'Pending'
    ASSIGNED = 1, 'Assigned'
    SENT = 2, 'Sent'
    DELIVERED = 3, 'Delivered'
    CANCELLED = 4, 'Cancelled'

class OrderStatus(models.Model):
    name = models.CharField(max_length=200)
    start_date = models.DateTimeField(auto_now_add=True)
    status = models.IntegerField(choices=OrderStatusValues.choices, default=OrderStatusValues.PENDING)
    cancelled_by_client = models.BooleanField(null=True)

    def is_assigned(self) -> bool:
        return self.status == OrderStatusValues.ASSIGNED

    def is_delivered(self) -> bool:
        return self.status == OrderStatusValues.DELIVERED

    def can_add_item(self) -> bool:
        return self.status == OrderStatusValues.PENDING

    def can_assign(self) -> bool:
        return self.status == OrderStatusValues.PENDING

    def can_refuse(self) -> bool:
        return self.status == OrderStatusValues.ASSIGNED

    def can_send(self) -> bool:
        return self.status == OrderStatusValues.ASSIGNED

    def can_deliver(self) -> bool:
        return self.status == OrderStatusValues.SENT

    def can_cancel(self) -> bool:
        return (self.status == OrderStatusValues.PENDING or
                self.status == OrderStatusValues.ASSIGNED)

    def assign(self) -> None:
        if self.can_assign():
            self.status = OrderStatusValues.ASSIGNED
            self.save()
        else:
            raise ValidationError("Cannot assign this order")

    def refuse(self) -> None:
        if self.can_refuse():
            self.status = OrderStatusValues.CANCELLED
            self.cancelled_by_client = False
            self.save()
        else:
            raise ValidationError("Cannot refuse this order")

    def send(self) -> None:
        if self.can_send():
            self.status = OrderStatusValues.SENT
            self.save()
        else:
            raise ValidationError("Cannot send this order")

    def cancel(self) -> None:
        if self.can_cancel():
            self.status = OrderStatusValues.CANCELLED
            self.cancelled_by_client = True
            self.save()
        else:
            raise ValidationError("Cannot cancel this order")

    def deliver(self) -> None:
        if self.can_deliver():
            self.status = OrderStatusValues.DELIVERED
            self.save()
        else:
            raise ValidationError("Cannot deliver this order")
