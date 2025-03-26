package org.foi.uzdiz.statePrugeStanice;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.factorypruga.Pruga;

public class KvarState implements State {
	@Override
    public void promijeniStatus(Pruga pruga, String stanica1, String stanica2) {
        pruga.postaviStatusRelacije(stanica1, stanica2, "K");
    }

    @Override
    public boolean jeDostupna(Pruga pruga, String stanica1, String stanica2) {
        return false;
    }
}
