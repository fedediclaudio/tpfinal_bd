# Generated by Django 3.2.9 on 2022-02-13 22:46

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='orderstatus',
            name='start_date',
            field=models.DateTimeField(auto_now_add=True),
        ),
    ]
