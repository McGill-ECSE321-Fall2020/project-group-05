import requests as r
import json
url = "http://localhost:8080/"
def get(endpoint,params=None,data=None):
    return r.get(url+endpoint,params=params,data=data)
def post(endpoint,params=None,data=None):
    return r.post(url+endpoint,params=params,data=data)

def pget(endpoint,params=None,data=None):
    c = get(endpoint,params=params,data=data)
    return c,c.content
def ppost(endpoint,params=None,data=None):
    c = post(endpoint,params=params,data=data)
    return c,c.content

print(pget("users/get_all"))
print(pget("managers/get_all"))
print(pget("artpiece/get_all"))
print(pget("artorder/get_all"))
print(pget("customers/get_all"))
print(pget("artlisting/get_all"))
print(pget("artists/get_all"))
print(pget("tags/get_all"))
print(pget("tickets/get_all"))

print(ppost("users/create",data={"emailAddress":"auryan898@gmail.com","displayname":"dingdong1","username":"hiIAmBilly1","password":"password","profilePicLink":None,"profileDescription":""}))
print(ppost("managers/create"))
print(ppost("artpiece/create"))
print(ppost("artorder/create"))
print(ppost("customers/create"))
print(ppost("artlisting/create"))
print(ppost("artists/create"))
print(ppost("tags/create"))
print(ppost("tickets/create"))