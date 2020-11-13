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
    return formurlencoded(obj, {
        ignorenull: true,
        sorted: true
    })
}

export function authenticateUser(username, password) {
    post('/users/login', parse({ 'username': username, 'password': password })).then(response => {
        let firebaseJWT = response.data.firebaseJWT; // 

        return firebase.auth().setPersistence(firebase.auth.Auth.Persistence.LOCAL).then(function(){
            return firebase.auth().signInWithCustomToken(firebaseJWT)
        })
    }).catch(error => {console.log(error)})
}

export function authenticateEmail(email,password) {
    post('/users/email_login', parse({ 'emailAddress': email, 'password': password })).then(response => {
        let firebaseJWT = response.data.firebaseJWT;
        return firebase.auth().signInWithCustomToken(firebaseJWT)
    }).catch(error => {console.log(error)})
}

export function unauthenticate(){
    return firebase.auth().signOut()
}

export function onFirebaseAuth(observer) {
    firebase.auth().onAuthStateChanged(observer)
}

export function retrieveCurrentUser() {
    return firebase.auth().currentUser
}