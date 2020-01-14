package ch.ergon.xcsbotcontrol.api.model;

import java.util.ArrayList;
import java.util.List;

public class DeviceSpecificationBuilder {
	private final DeviceSpecification spec = new DeviceSpecification();
	private List<DeviceFilterBuilder> filterBuilders = new ArrayList<>();

	public static DeviceSpecificationBuilder deviceSpecifiation() {
		return new DeviceSpecificationBuilder();
	}

	public DeviceSpecification build() {
		for (DeviceFilterBuilder filterBuilder : filterBuilders) {
			spec.filters.add(filterBuilder.build());
		}
		return spec;
	}

	public DeviceSpecificationBuilder addDeviceIdentifier(String identifier) {
		spec.deviceIdentifiers.add(identifier);
		return this;
	}

	public DeviceSpecificationBuilder addFilter(DeviceFilterBuilder filter) {
		filterBuilders.add(filter);
		return this;
	}
}
