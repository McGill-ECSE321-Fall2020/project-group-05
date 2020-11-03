import requests as r
import json
import sys
url = "https://vis-art-application.herokuapp.com/" # development server
# url = "http://localhost:8080/"
result = 0
def get(endpoint,params=None,data=None):
    return r.get(url+endpoint,params=params,data=data)
def post(endpoint,params=None,data=None):
    return r.post(url+endpoint,params=params,data=data)

def pget(endpoint,params=None,data=None,func=None):
    c = get(endpoint,params=params,data=data)
    print(">>>",c.url,c,file=sys.stderr)
    if func is None:
        return c,c.content
    else:
        return c,c.content,func(c)
def ppost(endpoint,params=None,data=None,func=None):
    c = post(endpoint,params=params,data=data)
    print(">>>",c.url,c,file=sys.stderr)
    if func is None:
        return c,c.content
    else:
        return c,c.content,func(c)

print('---Retrieve Everything Currently in the Database---')
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
    print()
    print('---All Create REST Methods---')
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


    # get individual methods
    print()
    print('---All get_all REST Methods---')
    customers_ids = pget("customers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artists_ids = pget("artists/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    managers_ids = pget("managers/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artpiece_ids = pget("artpiece/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artlisting_ids = pget("artlisting/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    tags_ids = pget("tags/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    tickets_ids = pget("tickets/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    artorder_ids = pget("artorder/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    users_ids = pget("users/get_all", func=lambda c: [i['idCode'] for i in c.json()])[2]
    
    print()
    print('---All Getter (retrieves one entity by id) REST Methods---')
    print("individual get customers",[pget("customers/get/"+id_code) for id_code in customers_ids])
    print("individual get artists",[pget("artists/get/"+id_code) for id_code in artists_ids])
    print("individual get managers",[pget("managers/get/"+id_code) for id_code in managers_ids])
    print("individual get artpiece",[pget("artpiece/get/"+id_code) for id_code in artpiece_ids])
    print("individual get artlisting",[pget("artlisting/get/"+id_code) for id_code in artlisting_ids])
    print("individual get tags",[pget("tags/get/"+id_code) for id_code in tags_ids])
    print("individual get tickets",[pget("tickets/get/"+id_code) for id_code in tickets_ids])
    print("individual get artorder",[pget("artorder/get/"+id_code) for id_code in artorder_ids])
    print("individual get users",[pget("users/get/"+id_code) for id_code in users_ids])

    # Update methods, any other remaining GET and POST methods
    """Artist API"""
    print()
    print("---Other Artist REST Controller methods---")
    artist1 = artists_create[0].json()['idCode']
    pget("/artists/get_all_keys")
    print(ppost("/artists/remove_listing/{idCode}/{listingId}".format(idCode=artist1,listingId=artlisting_create[0].json()['idCode'])))
    print(ppost("/artists/add_listing/{idCode}/{listingId}".format(idCode=artist1,listingId=artlisting_create[0].json()['idCode'])))
    print(ppost("/artists/remove_ticket/{idCode}/{ticketId}".format(idCode=artist1,ticketId=tickets_create[0].json()['idCode'])))
    print(ppost("/artists/add_ticket/{idCode}/{ticketId}".format(idCode=artist1,ticketId=tickets_create[0].json()['idCode'])))

    """User API"""
    print()
    print("---Other User REST Controller methods---")
    user1 = users_create[0].json()['idCode']
    print(ppost("users/update/{idCode}".format(idCode=user1),data={
        'profilePicLink':'google.com'
    }))

    """Manager API"""
    print()
    print('---Other Manager Rest Controller methods---')
    print(managers_create)
    manager1 = managers_create[0].json()['idCode']
    listing1 = artlisting_create[0].json()['idCode']
    print(ppost('managers/add_listing/{idCode}'.format(idCode=manager1), data={'listingIdCode':listing1}))
    manager1_listings = (pget("managers/get_listings/{idCode}".format(idCode=manager1)))
    print(manager1_listings)
    print(ppost('managers/remove_listing/{idCode}'.format(idCode=manager1), data={'listingIdCode':listing1}))

    """Customer API"""
    print()
    print('---Other Customer Rest Controller methods---')
    customer1 = customers_create[0].json()['idCode']
    # listing1 = listing1 # from earlier
    print(ppost("/customers/add_favorite_listing/{idCode}".format(idCode=customer1),data={
        "listingIdCode":listing1
    }))
    print(pget("/customers/get_favorite_listings/{idCode}".format(idCode=customer1)))
    print(ppost("/customers/remove_favorite_listing/{idCode}".format(idCode=customer1),data={
        "listingIdCode":listing1
    }))
    print(pget("/customers/get_favorite_listings/{idCode}".format(idCode=customer1)))
    print(pget("/customers/get_tickets/{idCode}".format(idCode=customer1)))

    """ArtListing API"""
    print()
    print('---Other ArtListing Rest Controller methods---')
    # listing1 = listing1 # from earlier
    print(ppost("artlisting/update/{idCode}".format(idCode=listing1),data={
        'aDescription':"yo i'm a sculpture"
    }))

    print(ppost("artlisting/update_dimensions/{idCode}".format(idCode=listing1),data={
        'dimensions':"[4.4,4.5,2.3]"
        }))

    print(ppost("artlisting/update_post_images/{idCode}".format(idCode=listing1),data={
        'images':"['duh.com','pfft.net']"
        }))
    piece1 = artpiece_create[0].json()['idCode']
    print(ppost("/artlisting/remove_piece/{idCode}".format(idCode=listing1),data={
        "artPieceId":piece1
    }))
    
    print(ppost("/artlisting/add_piece/{idCode}".format(idCode=listing1),data={
        "artPieceId":piece1
    }))

    print(pget("/artlisting/get_favorited_customers/{idCode}".format(idCode=listing1)))

    tag1 = tags_create[0].json()['idCode']
    print(ppost("/artlisting/remove_tag/{idCode}".format(idCode=listing1),data={
        'tagCode':tag1
    }))
    print(ppost("/artlisting/add_tag/{idCode}".format(idCode=listing1),data={
        'tagCode':tag1
    }))

    print(pget("/artlisting/get_unsold_art"))
    print(pget("/artlisting/get_artwork_by_keyword",params={
        'keywords':'hello,bob'
    }))


    """Tag API"""
    print()
    print('---Other Tag Rest Controller methods---')
    print(pget("/tags/get_by_type/{type}".format(type="Other")))
    print(pget("/tags/get_by_keyword/{keyword}".format(keyword="bob")))
    tag1 = tags_create[0].json()['idCode']
    print(ppost("/tags/update/{idCode}".format(idCode=tag1),data={
        'aKeyword':'hello'
    }))

    """ArtPiece API"""
    print()
    print('---Other ArtPiece Rest Controller methods---')
    piece1 = piece1 # from earlier
    print(ppost("/artpiece/update/{idCode}".format(idCode=piece1),data={
        'pieceLocation': 'AtGallery'
    }))
    order1 = artorder_create[0].json()['idCode']
    print(ppost("/artpiece/add_order/{idCode}".format(idCode=piece1),data={
        'artOrderId':order1
    }))

    """ArtOrder API"""
    print()
    print('---Other ArtOrder Rest Controller methods---')
    print(ppost("/artorder/update/{idCode}".format(idCode=order1),data={
        'aIsDelivered':'true'
    }))
    ticket1 = tickets_create[0].json()['idCode']
    print(ppost("/artorder/add_ticket/{idCode}/{ticketId}".format(idCode=order1,ticketId=ticket1)))

    """Ticket API"""
    print()
    print('---Other Ticket Rest Controller methods---')
    print(ppost("/tickets/update/{idCode}".format(idCode=ticket1),data={
        'aPaymentAmount':'69.00'
    }))
    
except Exception as e:
    print(e,file=sys.stderr)
    result = 1
print()
print("Cleaning Up...")
# Delete All entities
print('---All Deleting REST Methods (delete everything in database---')
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