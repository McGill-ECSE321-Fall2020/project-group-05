
<template>
<span class="container-fluid">
    <div class="row">
      <div class="col-md-4"/>
      <div class="col-md-4">
        <div class="card">
          <div class="card-header">
            <h4>Login</h4>
          </div>
          <div class="card-body">
            <span class="form-group" id="signin">
              <label for="inputEmail" class="sr-only">Email Address</label>
              <input
                type="email"
                id="inputEmail"
                class="form-control"
                name="emailAddress"
                placeholder="Email"
                required
                autofocus
              />
              <label for="inputPass" class="sr-only">Password</label>
              <input
                type="password"
                id="inputPass"
                class="form-control"
                name="password"
                placeholder="Password"
                required
              />
              <button
                class="btn btn-lg btn-primary btn-block"
                type="submit"
                v-on:click="authenticateUser"
              >
                Sign in
              </button>
              <div class="alert alert-warning" role="alert" v-if="!isSuccess">
                Login was not successful
              </div>
            </span>
          </div>
        </div>
      </div>
    </div>
</span>
</template>

<script>
/* eslint-disable */
var backend = require("@/tools/backend");
var storage = require("@/tools/cloud-storage");

function getElId(id) {
  return document.getElementById(id);
}

export default {
  name: "LoginPage",
  components: {},
  data: function() {
    return {
      isSuccess: true
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
    authenticateUser: function() {
      let vm = this;
      backend.authenticateEmail(
        getElId("inputEmail").value,
        getElId("inputPass").value
      );
      setTimeout(()=>
      backend.onFirebaseAuth(function(user) {
      if (user != null) {
        vm.isSuccess = true;
        vm.$router.push({path:'/'});
      } else {
        vm.isSuccess = false;
      }
      }),400);
    }
  }
};
</script>

