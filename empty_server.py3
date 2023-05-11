#!/usr/bin/env python3

from http.server import HTTPServer, SimpleHTTPRequestHandler
import os

def run(server_class=HTTPServer, handler_class=SimpleHTTPRequestHandler):
    """Entrypoint for python server"""
    server_address = ("0.0.0.0", int(os.getenv("PORT")))
    httpd = server_class(server_address, handler_class)
    print("running ...")
    httpd.serve_forever()

if __name__ == "__main__":
    run()

