package main.vue;

public class VueApplicationMobile extends VuePlateforme {

    VueAdministration vueAdministration;
    VueMembre vueMembre;
    VueProfessionnel vueProfessionnel;
    VueService vueService;

    public VueApplicationMobile() {
        this.vueAdministration = new VueAdministration();
        this.vueMembre = new VueMembre();
        this.vueProfessionnel = new VueProfessionnel();
        this.vueService = new VueService();
    }

    @Override
    public void menuAccueil() {

    }

    @Override
    public void authentification() {

    }
}
