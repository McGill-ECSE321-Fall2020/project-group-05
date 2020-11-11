<template>
  <form class="form-signin">
    <label for="inputEmail" class="sr-only">Email Address</label>
    <input
      type="email"
      id="inputEmail"
      class="form-control"
      name="emailAddress"
      placeholder="an email"
      required
      autofocus
    />
    <label for="inputPass" class="sr-only">Password</label>
    <input
      type="password"
      id="inputPass"
      class="form-control"
      name="password"
      placeholder="the password"
      required
    />
    <button class="btn btn-lg btn-primary btn-block" type="submit">
      Sign in
    </button>
  </form>
</template>

<script>
/* eslint-disable */
var backend = require("@/tools/backend");
var storage = require("@/tools/cloud-storage");

export default {
  name: "BasicLogin",
  Component: {},
  data: function() {
    return {};
  },
  props: [],
  methods: {},
  created: function() {
    storage.authenticate("bobby");
    
  }
};
</script>
