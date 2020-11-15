
<template>
<div class="container">
      <div class="py-5 text-center">
        <h2>Registration form</h2>
      </div>

      <div class="row">
        <div class="col-md">
          <form class="needs-validation" novalidate>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" placeholder="" required>
                <div class="invalid-feedback">
                  Valid username is required.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="displayName">Display name</label>
                <input type="text" class="form-control" id="displayName" placeholder="" required>
                <div class="invalid-feedback">
                  Valid display name is required.
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="email">Email</label>
              <div class="input-group">
                <input type="email" class="form-control" id="email" placeholder="" required>
                <div class="invalid-feedback" style="width: 100%;">
                  Valid email is required.
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="password">Password </label>
              <input type="password" class="form-control" id="password" placeholder="" required>
              <div class="invalid-feedback">
                Valid password required.
              </div>
            </div>
            <div class="mb-3">
              <label for="password2">Re-type password</label>
              <input type="password" class="form-control" id="password2" placeholder="" required>
              <div class="invalid-feedback">
                Valid password required.
              </div>
            </div>
            <div class="alert alert-warning" role="alert" v-show="!isSamePassword">
                Passwords do not match
            </div>
            <div class="custom-control custom-checkbox">
              <input type="checkbox" class="custom-control-input" id="isArtist">
              <label class="custom-control-label" for="isArtist">I want to be an Artist.</label>
            </div>
            <div class="col-md-6 mb-3">
                <label for="managerCode">Manager Code </label>
                <input type="text" class="form-control" id="managerCode" placeholder="VisArt">
            </div>
            <div class="alert alert-warning" role="alert" v-show="!isSuccess">
                Signup Failed
            </div>
            <div class="alert alert-warning" role="alert" v-show="!isCorrectManagerCode">
                Wrong Manager Id
            </div>

            <button class="btn btn-primary btn-lg btn-block" type="button" v-on:click="createAccount">Create Account</button>
          </form>
        </div>
      </div>
    </div>
</template>

<script>
/* eslint-disable */
var backend = require("@/tools/backend");
var storage = require("@/tools/cloud-storage");

function getElId(id) {
  return document.getElementById(id);
}

export default {
  name: "SignupPage",
  components: {},
  data: function() {
    return {
      isSuccess: true,
      isSamePassword: true,
      isCorrectManagerCode: true
    };
  },
  created: function() {
    let vm = this;
    backend.onFirebaseAuth(function(user) {
      if (user != null){
        vm.$router.push({path:'/'});
      }
      
    });
  },
  methods: {
    createAccount: function () {
      let vm = this;
      if (getElId("password").value != getElId("password2").value) {
          vm.isSamePassword=false;
      }else{
      vm.isSamePassword = true;
      vm.isSuccess = true;
      if (getElId("managerCode").value=="VisArt"){
        vm.isCorrectManagerCode=true;
        backend
        .post('/managers/create', backend.parse({
          emailAddress: getElId("email")?.value,
          displayname: getElId("displayName")?.value,
          username: getElId("username")?.value,
          password: getElId("password")?.value,
          profilePicLink: "",
          profileDescription: ""
        })
        )
        .then(function (response) {
            console.log(response)
            vm.$router.push({path:'/login'});
        })
        .catch(e => {
          vm.isSuccess=false;
          console.log(e)
        })

      }else if(getElId("managerCode").value==""){
      backend
        .post('/customers/create', backend.parse({
          emailAddress: getElId("email")?.value,
          displayname: getElId("displayName")?.value,
          username: getElId("username")?.value,
          password: getElId("password")?.value,
          profilePicLink: "",
          profileDescription: ""
        })
        )
        .then(function (response) {
            console.log(response)
            if (getElId("isArtist").value=="on"){
                backend.post('/artists/create', backend.parse({
                    customerId:response.data.idCode 
                })).then(function (r) {
                    console.log(r);
                })
            } 
            vm.$router.push({path:'/login'});
        })
        .catch(e => {
          vm.isSuccess=false;
          console.log(e)
        })
    }
    else{
        vm.isCorrectManagerCode=false;
    }  
    }
    }
  }
};
</script>

