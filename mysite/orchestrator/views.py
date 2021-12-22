from django.shortcuts import render
from django.http import HttpResponse
import pika
import requests

# Create your views here.


def send_message(request):
    print(request)
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
    channel = connection.channel()

    message = "Message from RESTfull service"
    channel.queue_declare(queue='hello')
    channel.basic_publish(exchange='',
                          routing_key='hello',
                          body=message)
    print(" [x] Sent,", message)
    connection.close()
    return HttpResponse(" [x] Sent," + message)


def callback(request, ch, method, properties, body):
    print(" [x] Received %r" % body)


def receive_message(request):
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
    channel = connection.channel()

    channel.queue_declare(queue='hello')
    channel.basic_consume(queue='hello',
                          auto_ack=True,
                          on_message_callback=callback)

    print(' [*] Waiting for messages. To exit press CTRL+C')
    channel.start_consuming()
