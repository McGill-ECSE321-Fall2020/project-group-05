/* eslint-disable */
import axios from 'axios'
import formurlencoded from 'form-urlencoded'
import firebase from 'firebase'


var config = require('../../config')
var frontendUrl = config.site
var backendUrl = config.backend.site

var axPost = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl, 'Content-Type': 'application/x-www-form-urlencoded' }
})

var axGet = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export let get = axGet.get
export let post = axPost.post
export let parse = function (obj) {
    let arr = []
    for (let key in obj) {
        if (Array.isArray(obj[key])) {
            arr.push(parseList(key, obj[key]))
        }
    }

    return arr.join('&') + '&' + formurlencoded(obj, {
        ignorenull: true,
        sorted: true
    })
}

export let parseList = function (key, list) {
    return list.map(item => encodeURI(key) + '=' + encodeURI(item)).join("&")
}

export function authenticateUser(username, password) {
    post('/users/login', parse({ 'username': username, 'password': password })).then(response => {
        let firebaseJWT = response.data.firebaseJWT; // 

        return firebase.auth().setPersistence(firebase.auth.Auth.Persistence.LOCAL).then(function () {
            return firebase.auth().signInWithCustomToken(firebaseJWT)
        })
    }).catch(error => { console.log(error) })
}

export function authenticateEmail(email, password) {
    post('/users/email_login', parse({ 'emailAddress': email, 'password': password })).then(response => {
        let firebaseJWT = response.data.firebaseJWT;
        return firebase.auth().signInWithCustomToken(firebaseJWT)
    }).catch(error => { console.log(error) })
}

export function unauthenticate() {
    return firebase.auth().signOut()
}

export function onFirebaseAuth(observer) {
    firebase.auth().onAuthStateChanged(observer)
}

export function retrieveCurrentUser() {
    return firebase.auth().currentUser
}

// this function also returns the userId for when it is done, null if not logged in
// listener takes the form function(customerId, artistId, managerId){}, 
// and the fields are null when the id is not available
export function performUserAction(listener, errorFunc) {
    let user = retrieveCurrentUser()
    if (user != null) {
        get('customers/get/' + user.uid).then(function (response) {
            if (response.data.artistId && response.data.artistId != '') {
                listener(response.data.idCode, response.data.artistId, null)
            } else {
                listener(response.data.idCode, null, null)
            }
        }).catch(function (error) {
            return backend.get('managers/get/' + user.uid)
        }).then(function (response) {
            listener(null, null, response.data.idCode)
        }).catch(errorFunc)
        return user.uid
    } else {
        return null
    }
}