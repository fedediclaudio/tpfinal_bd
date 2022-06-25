from djongo import models

class ProductType(models.Model):
    _id = models.ObjectIdField(primary_key=True)
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200)
