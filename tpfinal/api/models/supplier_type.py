from djongo import models

class SupplierType(models.Model):
    _id = models.ObjectIdField(primary_key=True)
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200)
