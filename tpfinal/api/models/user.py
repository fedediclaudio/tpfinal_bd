from djongo import models

class User(models.Model):
    _id = models.ObjectIdField(primary_key=True)
    name = models.CharField(max_length=200)
    username = models.CharField(max_length=200)
    password = models.CharField(max_length=200)
    email = models.CharField(max_length=200)
    date_of_birth = models.DateField()
    active = models.BooleanField(default=True)
    score = models.IntegerField(default=0)

    # Needed so that we don't actually create a "User" table
    class Meta:
        abstract = True
