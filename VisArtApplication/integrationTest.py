import requests as r
import json
url = "https://vis-art-application.herokuapp.com/"
result = 0
def get(endpoint,params=None,data=None):
    return r.get(url+endpoint,params=params,data=data)
def post(endpoint,params=None,data=None):
    return r.post(url+endpoint,params=params,data=data)

def pget(endpoint,params=None,data=None,func=None):
    c = get(endpoint,params=params,data=data)
    print(c.url,c)
    if func is None:
        return c,c.content
    else:
        return c,c.content,func(c)
def ppost(endpoint,params=None,data=None,func=None):
    c = post(endpoint,params=params,data=data)
    print(c.url,c)
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

try:
    # Create a bunch of entities
    users_create = (ppost("users/create",data={"emailAddress":"auryan898@gmail.com","displayname":"dingdong1","username":"hiIAmBilly1","password":"password","profilePicLink":None,"profileDescription":""}))
    print(users_create)
    managers_create = (ppost("managers/create",data={"emailAddress":"auryan898@gmail.com","displayname":"dingdong2","username":"hiIAmBilly2","password":"password","profilePicLink":None,"profileDescription":""}))
    print(managers_create)
    customers_create = (ppost("customers/create",data={"emailAddress":"auryan898@gmail.com","displayname":"dingdong4","username":"hiIAmBilly4","password":"password","profilePicLink":".","profileDescription":""}))
    print(customers_create)
    # customers_ids = pget("customers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artists_create = (ppost("artists/create",data={'customerId':customers_create[0].json()['idCode']}))
    print(artists_create)
    # artists_ids = pget("artists/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artlisting_create = (ppost("artlisting/create",data={'aVisibility':'Public','aDescription':'none','aTitle':'My Doodle','artistId':artists_create[0].json()['idCode']}))
    print(artlisting_create)
    # artlisting_ids = pget("artlisting/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    tags_create = (ppost("tags/create",data={"aListing":artlisting_create[0].json()['idCode'],'aKeyword':'bob','aType':'Other'}))
    print(tags_create)
    artpiece_create = (ppost("artpiece/create",data={'aArtListingId':artlisting_create[0].json()['idCode'],'pieceLocation':'Offsite','aAddressLocation':'346 somewhere st, nowhere, QC, CA'}))
    print(artpiece_create)
    # artpiece_ids = pget("artpiece/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artorder_create = (ppost("artorder/create",data={'aIsDelivered':'false','pieceLocation':'Offsite','aTargetAddress':'345 myhouse ave, nowhere, QC, CA','aDeliveryTracker':'canadapost.ca','artPieceId':artpiece_create[0].json()['idCode']}))
    print(artorder_create)
    # artorder_ids = pget("artorder/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    tickets_create = (ppost("tickets/create",data={'aIsPaymentConfirmed':'false','aPaymentAmount':'43.99','aOrder':artorder_create[0].json()['idCode'],'aCustomer':customers_create[0].json()['idCode'],'aArtist':artists_create[0].json()['idCode']}))
    print(tickets_create)

    # Testing update methods

    # get individual methods
    customers_ids = pget("customers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artists_ids = pget("artists/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    managers_ids = pget("managers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artpiece_ids = pget("artpiece/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artlisting_ids = pget("artlisting/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    tags_ids = pget("tags/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    tickets_ids = pget("tickets/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artorder_ids = pget("artorder/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    users_ids = pget("users/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    print("individual get customers",[pget("customers/get/"+id_code) for id_code in customers_ids])
    print("individual get artists",[pget("artists/get/"+id_code) for id_code in artists_ids])
    print("individual get managers",[pget("managers/get/"+id_code) for id_code in managers_ids])
    print("individual get artpiece",[pget("artpiece/get/"+id_code) for id_code in artpiece_ids])
    print("individual get artlisting",[pget("artlisting/get/"+id_code) for id_code in artlisting_ids])
    print("individual get tags",[pget("tags/get/"+id_code) for id_code in tags_ids])
    print("individual get tickets",[pget("tickets/get/"+id_code) for id_code in tickets_ids])
    print("individual get artorder",[pget("artorder/get/"+id_code) for id_code in artorder_ids])
    print("individual get users",[pget("users/get/"+id_code) for id_code in users_ids])
except:
    result = 1
# Delete All entities
customers_ids = pget("customers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
artists_ids = pget("artists/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
managers_ids = pget("managers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
artpiece_ids = pget("artpiece/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
artlisting_ids = pget("artlisting/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
tags_ids = pget("tags/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
tickets_ids = pget("tickets/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
artorder_ids = pget("artorder/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
users_ids = pget("users/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]


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
exit(result)