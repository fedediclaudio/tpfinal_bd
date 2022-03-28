from django.db import models
from .user import User

class DeliveryMan(User):
    number_of_success = models.IntegerField(default=0)
    free = models.BooleanField(default=True)
    date_of_admission = models.DateField(auto_now_add=True)

    def cancelled_order(self):
        self.score -= 2
        self.save()

    def delivered_order(self):
        self.score += 1
        self.free = True
        self.save()

    def occupy(self):
        self.free = False
        self.save()
