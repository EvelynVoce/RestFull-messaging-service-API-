from django.urls import path
from . import views

urlpatterns = [
    path('', views.generate_id, name='generate_id'),
]