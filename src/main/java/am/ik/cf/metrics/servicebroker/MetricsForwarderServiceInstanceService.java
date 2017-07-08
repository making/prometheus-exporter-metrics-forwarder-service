package am.ik.cf.metrics.servicebroker;

import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MetricsForwarderServiceInstanceService implements ServiceInstanceService {
	private final ServiceInstanceRepository serviceInstanceRepository;

	public MetricsForwarderServiceInstanceService(
			ServiceInstanceRepository serviceInstanceRepository) {
		this.serviceInstanceRepository = serviceInstanceRepository;
	}

	@Override
	public CreateServiceInstanceResponse createServiceInstance(
			CreateServiceInstanceRequest request) {
		this.serviceInstanceRepository.save(request.getServiceInstanceId(),
				request.getSpaceGuid());
		return new CreateServiceInstanceResponse();
	}

	@Override
	public GetLastServiceOperationResponse getLastOperation(
			GetLastServiceOperationRequest request) {
		return new GetLastServiceOperationResponse();
	}

	@Override
	public DeleteServiceInstanceResponse deleteServiceInstance(
			DeleteServiceInstanceRequest request) {
		this.serviceInstanceRepository.delete(request.getServiceInstanceId());
		return new DeleteServiceInstanceResponse();
	}

	@Override
	public UpdateServiceInstanceResponse updateServiceInstance(
			UpdateServiceInstanceRequest request) {
		return new UpdateServiceInstanceResponse();
	}
}
