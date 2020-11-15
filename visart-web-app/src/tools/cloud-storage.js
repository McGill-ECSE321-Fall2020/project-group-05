/* eslint-disable */
import firebase from 'firebase'
import { v4 as uuidv4 } from 'uuid';
var backend = require('@/tools/backend')

function genId() {
    return uuidv4()
}

function getExtension(filename) {
    return '.' + filename.split('.').pop()
}

export function read(storedImageName) {
    let ref = firebase.storage().ref('postedImages')
    return ref.child(storedImageName).getDownloadURL()
}

export function createStoredName(filename) {
    return genId() + getExtension(filename)
}

export function write(storedImageName, file) {
    let ref = firebase.storage().ref('postedImages')
    return ref.child(storedImageName).put(file)
}

export function writeSafe(file) {
    let filename = createStoredName(file.name)
    return Promise.all([filename, write(filename, file)])
}