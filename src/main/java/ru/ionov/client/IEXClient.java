package ru.ionov.client;

import ru.ionov.model.IEXInfo;

import java.util.Collection;
import java.util.Map;

public interface IEXClient {

    Map<String, IEXInfo> getInfoForSymbols(Collection<String> symbols);
}
