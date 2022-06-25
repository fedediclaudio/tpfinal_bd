from djongo import models
from .client import Client

# Create your models here.
class Address(models.Model):
    _id = models.ObjectIdField(primary_key=True)
    name = models.CharField(max_length=200)
    address = models.CharField(max_length=200)
    apartment = models.CharField(max_length=200, null=True)
    coordX = models.FloatField()
    coordY = models.FloatField()
    description = models.CharField(max_length=200, null=True)

    # dependencies with other models
    client = models.ForeignKey(Client, on_delete=models.PROTECT, related_name='addresses')
