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

print("customers",pget("customers/get_all"))
print("artists",pget("artists/get_all"))
print("users",pget("users/get_all"))
print("managers",pget("managers/get_all"))
print("artpiece",pget("artpiece/get_all"))
print("artlisting",pget("artlisting/get_all"))
print("tags",pget("tags/get_all"))
print("tickets",pget("tickets/get_all"))
print("artorder",pget("artorder/get_all"))
print(ppost("users/create",data={"emailAddress":"auryan898@gmail.com","displayname":"dingdong1","username":"hiIAmBilly1","password":"password","profilePicLink":None,"profileDescription":""}))
print(ppost("managers/create",data={"emailAddress":"auryan898@gmail.com","displayname":"dingdong2","username":"hiIAmBilly2","password":"password","profilePicLink":None,"profileDescription":""}))
print(ppost("customers/create",data={"emailAddress":"auryan898@gmail.com","displayname":"dingdong4","username":"hiIAmBilly4","password":"password","profilePicLink":".","profileDescription":""}))
print(ppost("artists/create",data={'customerId':'0ee1f9e5-2afb-4ff1-8ad7-81798704d1af'}))
print(ppost("artlisting/create",data={'aVisibility':'Public','aDescription':'none','aTitle':'My Doodle','artistId':'3e5c116c-16ea-412e-8d6e-5fa89432104a'}))
print(ppost("tags/create",data={"aListing":'788257e2-94fc-410d-9ad6-ad0183854f70','aKeyword':'bob','aType':'Other'}))
print(ppost("artpiece/create",data={'aArtListingId':'788257e2-94fc-410d-9ad6-ad0183854f70','pieceLocation':'Offsite','aAddressLocation':'346 somewhere st, nowhere, QC, CA'}))
print(ppost("artorder/create",data={'aIsDelivered':'false','pieceLocation':'Offsite','aTargetAddress':'345 myhouse ave, nowhere, QC, CA','aDeliveryTracker':'canadapost.ca','artPieceDto':'b9a2fa83-83f5-492c-b29c-457f5d6af545'}))
print(ppost("tickets/create",data={'aIsPaymentConfirmed':'false','aPaymentAmount':'43.99','aOrder':'e02cccf3-cdb6-4b36-b12a-9cad2df137d8','aCustomer':'0ee1f9e5-2afb-4ff1-8ad7-81798704d1af','aArtist':'3e5c116c-16ea-412e-8d6e-5fa89432104a'}))