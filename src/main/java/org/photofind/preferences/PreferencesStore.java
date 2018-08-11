package org.photofind.preferences;

import org.photofind.net.Subscription;

import java.util.prefs.Preferences;

public class PreferencesStore {

    private static Preferences pref = Preferences.userRoot().node(PreferencesStore.class.getName());

    public static void saveProgramPreferences() {
        switch (Subscription.getCurrentSubscriptionProvider()) {
            case GOOGLE_CLOUD:
                pref.put(PreferenceKeys.VISION_PROVIDER_PREF, PreferenceKeys.VISION_PROVIDER_GCLOUD);
                break;
            case MICROSOFT_VISION:
                pref.put(PreferenceKeys.VISION_PROVIDER_PREF, PreferenceKeys.VISION_PROVIDER_MS);
                break;
        }

        pref.put(PreferenceKeys.VISION_API_KEY, Subscription.getSubscriptionKey());
    }

    public static void loadProgramPreferences() {
        String storedVisionProviderPreference = pref.get(PreferenceKeys.VISION_PROVIDER_PREF, null);

        if (storedVisionProviderPreference != null) {
            if (storedVisionProviderPreference.equals(PreferenceKeys.VISION_PROVIDER_MS)) {
                Subscription.setCurrentSubscriptionProvider(Subscription.Provider.MICROSOFT_VISION);
            } else if (storedVisionProviderPreference.equals(PreferenceKeys.VISION_PROVIDER_GCLOUD)) {
                Subscription.setCurrentSubscriptionProvider(Subscription.Provider.GOOGLE_CLOUD);
            }
        } else {
            Subscription.setCurrentSubscriptionProvider(Subscription.Provider.NONE);
        }

        Subscription.setSubscriptionKey(pref.get(PreferenceKeys.VISION_API_KEY, ""));
    }
}
