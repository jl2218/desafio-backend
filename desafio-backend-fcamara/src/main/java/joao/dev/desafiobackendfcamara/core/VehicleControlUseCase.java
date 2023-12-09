package joao.dev.desafiobackendfcamara.core;

import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;

public interface VehicleControlUseCase {

    Establishment vehicleEntryControl(String establishmentDocument, String vehiclePlate);

    Establishment vehicleExitControl(String establishmentDocument, String vehiclePlate);

    String entriesSummary(String establishmentDocument);

    String exitsSummary(String establishmentDocument);
}
