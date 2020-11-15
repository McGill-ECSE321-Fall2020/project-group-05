<template>
  <div class="row">
    <div class="col-75">
      <div class="container">
        <div class="row">
          <div class="col-50">
            <h3>Hello {{ userdata.user.displayname }}</h3>
            <h4>This Art is Almost Yours!</h4>
            <h3></h3>
            <div id="sectionForm" class="form-inline">
              <label for="AtGallery" class="form-check-label"
                >Pick up at gallery</label
              >
              <input
                class="leftAlign form-check-input"
                name="AtGallery"
                id="AtGallery"
                type="radio"
                v-model="pieceLocation"
                value="AtGallery"
              />
              <br />
              <label for="Offsite">Ship to address</label>
              <input
                class="leftAlign"
                name="Offsite"
                id="Offsite"
                type="radio"
                v-model="pieceLocation"
                value="Offsite"
              />
              <br />

              <input
                type="text"
                name="address"
                placeholder="Please enter your address here"
                id="addressButton"
                v-if="isVisibleAddress"
                v-model="targetAddress"
              />

              <input
                type="submit"
                value="Place Order"
                class="tagBtn"
                v-on:click="purchaseArtwork()"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
// backend.js has on it: backend.get(path,params), backend.post(path,data), backend.parse(json) => post data
var backend = require("@/tools/backend");
export default {
  data: function() {
    return {
      pieceId: "",
      pieceLocation: "AtGallery",
      targetAddress: "",
      listingId: "",
      artlisting: {},
      userdata: {
        user: { displayname: "" },
        customer: {},
        manager: {}
      }
    };
  },
  computed: {
    isVisibleAddress: function() {
      return this.pieceLocation == "Offsite";
    }
  },
  created: function() {
    this.listingId = this.$route.params.id;
    console.log("working");
    backend.onFirebaseAuth(user => {
      this.loadUserData();
    });
    this.loadArtListing();
  },
  methods: {
    purchaseArtwork: function() {
      let vm = this;
      backend
        .post(
          "/artorder/create",
          backend.parse({
            aIsDelivered: false,
            pieceLocation: vm.pieceLocation,
            aTargetAddress: vm.targetAddress != '' ? vm.targetAddress : 'TBD',
            aDeliveryTracker: "TBD",
            artPieceId: vm.artlisting.artPieces[0].idCode
          })
        )
        .then(resp => {
          return backend.post(
            "/tickets/create",
            backend.parse({
              aIsPaymentConfirmed: false,
              aPaymentAmount: vm.artlisting.price,
              aOrder: resp.data.idCode,
              aCustomer: vm.userdata.customer.idCode,
              aArtist: vm.artlisting.artist
            })
          );
        })
        .then(resp => {
          console.log(resp.data);
        });
    },
    loadUserData: function() {
      let userf = backend.retrieveCurrentUser();
      console.log(userf);
      let id = userf ? userf.uid : null;
      let vm = this;
      if (id != null && id != "") {
        backend
          .get("users/get/" + id)
          .then(resp => {
            vm.userdata.user = resp.data;

            console.log(resp);
            switch (vm.userdata.user.role) {
              case "Manager":
                return backend.get("managers/get/" + id);
              case "Customer":
                return backend.get("customers/get/" + id);
              default:
                vm.userdata.user.role = "User";
                return resp;
            }
          })
          .then(resp => {
            switch (vm.userdata.user.role) {
              case "Manager":
                vm.userdata.maanger = resp.data;
                break;
              case "Customer":
                vm.userdata.customer = resp.data;
                break;
              default:
            }
            return resp;
          });
        return true;
      } else return false;
    },
    loadArtListing: function() {
      if (this.listingId != null && this.listingId != "")
        backend.get("artlisting/get/" + this.listingId).then(resp => {
          this.artlisting = resp.data;
        });
    }
  }
};
</script>

<style>
.row {
  display: -ms-flexbox; /* IE10 */
  display: flex;
  -ms-flex-wrap: wrap; /* IE10 */
  flex-wrap: wrap;
  margin: 0 -16px;
}

.col-25 {
  -ms-flex: 25%; /* IE10 */
  flex: 25%;
}

.col-50 {
  -ms-flex: 50%; /* IE10 */
  flex: 50%;
}

.col-75 {
  -ms-flex: 75%; /* IE10 */
  flex: 75%;
}

.col-25,
.col-50,
.col-75 {
  padding: 0 16px;
}

.container {
  background-color: #f2f2f2;
  padding: 5px 20px 15px 20px;
  border: 1px solid lightgrey;
  border-radius: 3px;
}

input[type="radio"] {
  margin-bottom: 0px !important;
  width: 25px;
}

input[type="text"] {
  width: 100%;
  margin-bottom: 20px;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

label {
  margin-bottom: 10px;
  display: block;
}

.icon-container {
  margin-bottom: 20px;
  padding: 7px 0;
  font-size: 24px;
}

span.price {
  float: right;
  color: grey;
}

.titleAdjust {
  text-align: left;
  line-height: 150%;
  font-size: 1em;
}

.leftAlign {
  text-align: left;
}

/* Responsive layout - when the screen is less than 800px wide, make the two columns stack on top of each other instead of next to each other (and change the direction - make the "cart" column go on top) */
@media (max-width: 800px) {
  .row {
    flex-direction: column-reverse;
  }
  .col-25 {
    margin-bottom: 20px;
  }
}
</style>
