package org.foi.uzdiz.statePrugeStanice;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.factorypruga.Pruga;

public interface State {
	void promijeniStatus(Pruga pruga, String stanica1, String stanica2);
    boolean jeDostupna(Pruga pruga, String stanica1, String stanica2);
}
