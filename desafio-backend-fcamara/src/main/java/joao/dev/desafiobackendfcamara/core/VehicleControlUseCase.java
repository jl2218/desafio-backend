package joao.dev.desafiobackendfcamara.core;

import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;

import java.time.LocalDateTime;

public interface VehicleControlUseCase {

    Establishment vehicleEntryControl(String establishmentDocument, String vehiclePlate);

    Establishment vehicleExitControl(String establishmentDocument, String vehiclePlate);

    String summary(String establishmentDocument);

    String summaryPerHour(String establishmentDocument);

    Establishment vehicleEntryControlForCustomer(String establishmentDocument, String customerDocument);

    Establishment vehicleExitControlForCustomer(String establishmentDocument, String customerDocument);
}
