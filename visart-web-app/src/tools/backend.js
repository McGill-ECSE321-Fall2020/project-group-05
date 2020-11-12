/* eslint-disable */
import axios from 'axios'
import formurlencoded from 'form-urlencoded'

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

export function authenticate(username, password) {
    post('/users/login',parse({'username':username,'password':password}))
}