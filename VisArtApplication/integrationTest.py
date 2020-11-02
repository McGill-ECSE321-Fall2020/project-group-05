import requests as r
import json
url = "http://localhost:8080/"
def get(endpoint,params=None,data=None):
    return r.get(url+endpoint,params=params,data=data)
def post(endpoint,params=None,data=None):
    return r.post(url+endpoint,params=params,data=data)

def pget(endpoint,params=None,data=None,func=None):
    c = get(endpoint,params=params,data=data)
    if func is None:
        return c,c.content
    else:
        return c,c.content,func(c)
def ppost(endpoint,params=None,data=None,func=None):
    c = post(endpoint,params=params,data=data)
    if func is None:
        return c,c.content
    else:
        return c,c.content,func(c)

print("customers",pget("customers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2])
print("artists",pget("artists/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2])
print("users",pget("users/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2])
print("managers",pget("managers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2])
print("artpiece",pget("artpiece/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2])
print("artlisting",pget("artlisting/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2])
print("tags",pget("tags/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2])
print("tickets",pget("tickets/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2])
print("artorder",pget("artorder/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2])

# Create a bunch of entities
print(ppost("users/create",data={"emailAddress":"auryan898@gmail.com","displayname":"dingdong1","username":"hiIAmBilly1","password":"password","profilePicLink":None,"profileDescription":""}))
print(ppost("managers/create",data={"emailAddress":"auryan898@gmail.com","displayname":"dingdong2","username":"hiIAmBilly2","password":"password","profilePicLink":None,"profileDescription":""}))
print(ppost("customers/create",data={"emailAddress":"auryan898@gmail.com","displayname":"dingdong4","username":"hiIAmBilly4","password":"password","profilePicLink":".","profileDescription":""}))
customers_ids = pget("customers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
print(ppost("artists/create",data={'customerId':customers_ids[0]}))
artists_ids = pget("artists/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
print(ppost("artlisting/create",data={'aVisibility':'Public','aDescription':'none','aTitle':'My Doodle','artistId':artists_ids[0]}))
artlisting_ids = pget("artlisting/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
print(ppost("tags/create",data={"aListing":artlisting_ids[0],'aKeyword':'bob','aType':'Other'}))
print(ppost("artpiece/create",data={'aArtListingId':artlisting_ids[0],'pieceLocation':'Offsite','aAddressLocation':'346 somewhere st, nowhere, QC, CA'}))
artpiece_ids = pget("artpiece/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
print(ppost("artorder/create",data={'aIsDelivered':'false','pieceLocation':'Offsite','aTargetAddress':'345 myhouse ave, nowhere, QC, CA','aDeliveryTracker':'canadapost.ca','artPieceId':artpiece_ids[0]}))
artorder_ids = pget("artorder/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
print(ppost("tickets/create",data={'aIsPaymentConfirmed':'false','aPaymentAmount':'43.99','aOrder':artorder_ids[0],'aCustomer':customers_ids[0],'aArtist':artists_ids[0]}))

# Delete All entities
customers_ids = pget("customers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
artists_ids = pget("artists/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
managers_ids = pget("managers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
artpiece_ids = pget("artpiece/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
artlisting_ids = pget("artlisting/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
tags_ids = pget("tags/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
tickets_ids = pget("tickets/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
artorder_ids = pget("artorder/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]

print("delete tickets",     [ppost("tickets/delete/{}".format(id_code)) for id_code in tickets_ids])
print("delete artorder",    [ppost("artorder/delete/{}".format(id_code)) for id_code in artorder_ids])
print("delete artpiece",    [ppost("artpiece/delete/{}".format(id_code)) for id_code in artpiece_ids])
print("delete tags",        [ppost("tags/delete/{}".format(id_code)) for id_code in tags_ids])
print("delete artlisting",  [ppost("artlisting/delete/{}".format(id_code)) for id_code in artlisting_ids])
print("delete artists",     [ppost("artists/delete/{}".format(id_code)) for id_code in artists_ids])
print("delete customers",   [ppost("customers/delete/{}".format(id_code)) for id_code in customers_ids])
print("delete managers",    [ppost("managers/delete/{}".format(id_code)) for id_code in managers_ids])
users_ids = pget("users/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
print("delete users",       [ppost("users/delete/{}".format(id_code)) for id_code in users_ids])
