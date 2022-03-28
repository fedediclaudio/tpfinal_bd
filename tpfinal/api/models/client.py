from django.db import models
from .user import User

class Client(User):
    date_of_register = models.DateField(auto_now_add=True)

    def cancelled_order(self):
        self.score -= 1
        self.save()

    def received_order(self):
        self.score += 1
        self.save()
