package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {

	protected String name;
	List<String> altitudeRanges = new ArrayList<>();
	protected Map<String, Municipality> municipalities = new HashMap<>();
	protected Map<String, MountainHut> mountainHuts = new HashMap<>();

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		this.altitudeRanges = orderRanges(ranges);
	}
	

	private List<String> orderRanges (String... ranges) {
		List<String> orderedRanges = new ArrayList<>(Arrays.asList(ranges));
		Collections.sort(orderedRanges, (r1, r2) -> {
			String[] parts1 = r1.split("-");
			String[] parts2 = r2.split("-");
			Integer min1 = Integer.parseInt(parts1[0]);
			Integer min2 = Integer.parseInt(parts2[0]);
			return min1.compareTo(min2);
		});
		return orderedRanges;
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		for (String range : altitudeRanges) {
			String[] parts = range.split("-");
			Integer min = Integer.parseInt(parts[0]);
			Integer max = Integer.parseInt(parts[1]);
			if (altitude >= min && (max == -1 || altitude <= max)) {
				return range;
			}
		}
		return "0-INF";
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return municipalities.values();
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return mountainHuts.values();
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		if (municipalities.containsKey(name)){
			return municipalities.get(name);
		} else {
			Municipality municipality = new Municipality(name, province, altitude);
			municipalities.put(name, municipality);
			return municipality;
		}
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber, Municipality municipality) {
		if (mountainHuts.containsKey(name)){
			return mountainHuts.get(name);
		} else {
			MountainHut mountainHut = new MountainHut(name, category, bedsNumber, municipality);
			mountainHuts.put(name, mountainHut);
			return mountainHut;
		}

	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		if (mountainHuts.containsKey(name)){
			return mountainHuts.get(name);
		} else {
			MountainHut mountainHut = new MountainHut(name, altitude, category, bedsNumber, municipality);
			mountainHuts.put(name, mountainHut);
			return mountainHut;
		}
	}

	/**
	 * Creates a new region and loads its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(file);
		Region region = new Region(name);
		List<String> lines = readData(file);
		for (String line : lines){
			if (line.equals(lines.get(0))) {
				continue;
			}
			String province = line.split(";")[0];
			String municipalityName = line.split(";")[1];
			Integer municipalityAltitude = Integer.parseInt(line.split(";")[2]);
			String mountainHutName = line.split(";")[3];
			Optional<Integer> mountainHutAltitude = line.split(";")[4].isEmpty() 
    		? Optional.empty() 
    		: Optional.ofNullable(Integer.valueOf(line.split(";")[4]));
			String mountainHutCategory = line.split(";")[5];
			Integer mountainHutBedsNumber = Integer.parseInt(line.split(";")[6]);
			region.createOrGetMunicipality(municipalityName, province, municipalityAltitude);
			if (mountainHutAltitude.isPresent()) {
				region.createOrGetMountainHut(mountainHutName, mountainHutAltitude.get(), mountainHutCategory, mountainHutBedsNumber, region.municipalities.get(municipalityName));
			} else {
				region.createOrGetMountainHut(mountainHutName, mountainHutCategory, mountainHutBedsNumber, region.municipalities.get(municipalityName));
			}
			
		}
		return region;
	}

	/**
	 * Reads the lines of a text file.
	 *
	 * @param file path of the file
	 * @return a list with one element per line
	 */
	public static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().toList();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
    return municipalities.values().stream()
			.collect(Collectors.groupingBy(Municipality::getProvince, Collectors.counting()));
	// Occurences of each province
}




	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return mountainHuts.values().stream()
			.collect(Collectors.groupingBy(hut -> hut.getMunicipality().getProvince(), //First map with province as key
			Collectors.groupingBy( //On each resulting collection of MountainHuts, we group by municipality
				hut -> hut.getMunicipality().getName(), //Second map with municipality as key
				Collectors.counting()))); //For each resulting collection of MountainHuts, we count how many there are
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		return mountainHuts.values().stream()
			.collect(Collectors.groupingBy(hut -> this.getAltitudeRange(
				hut.getAltitude().orElse(hut.getMunicipality().getAltitude())
			),Collectors.counting()));
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return mountainHuts.values().stream()
		.collect(Collectors.groupingBy(
			hut -> hut.getMunicipality().getProvince(),
			Collectors.summingInt(MountainHut::getBedsNumber)));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		return mountainHuts.values().stream()
    // Group by altitude range
    .collect(Collectors.groupingBy(
        // For each hut, get its altitude range, which we use as key
        hut -> this.getAltitudeRange(
            hut.getAltitude().orElse(hut.getMunicipality().getAltitude())
        ),
        // Now for each group of huts, we map them to their number of beds
        Collectors.mapping(
            MountainHut::getBedsNumber,
            // For each collection of numbers, get max
            Collectors.maxBy(Comparator.naturalOrder())
        )
    ));


	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		return municipalities.values().stream()
			.collect(Collectors.groupingBy(mun -> 
			mountainHuts.values().stream()
				.filter(hut -> hut.getMunicipality().getName().equals(mun.getName()))
				.count(),
			Collectors.mapping(Municipality::getName, Collectors.collectingAndThen(
				Collectors.toList(), 
				list -> list.stream().sorted().collect(Collectors.toList())))));
	}

}
