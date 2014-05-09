package com.opitz.iotprototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.services.ElroPowerPlugService;
import com.opitz.iotprototype.utils.DataNotFoundException;

/**
 * This Controller manages all switch interaction, so basically on and off
 * adding deleting and querying.
 * <p>
 * User: Pascal Date: 07.10.13 Time: 12:31
 */

@Controller
@RequestMapping("/service/switches")
public class SwitchController {

	@Autowired
	ElroPowerPlugService elroPowerPlugService;

	/**
	 * Activate a certain {@link ElroPowerPlug}.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code PUT .../switches/activate/<exampleId><br/>
	 * </pre>
	 * 
	 * @param id
	 *            certain {@link ElroPowerPlug} id
	 * @return certain {@link ElroPowerPlug} retreived from the DB after
	 *         updating the lastKnownState
	 */
	@ResponseBody
	@RequestMapping(value = "/activate/{id}", method = RequestMethod.PUT)
	public ElroPowerPlug activate(@PathVariable("id") Integer id) {
		elroPowerPlugService.setState(id, true);
		return elroPowerPlugService.load(id);
	}

	/**
	 * Deactivate a certain {@link ElroPowerPlug}.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code PUT .../switches/deactivate/<exampleId><br/>
	 * </pre>
	 * 
	 * @param id
	 *            certain {@link ElroPowerPlug} id
	 * @return certain {@link ElroPowerPlug} retreived from the DB after
	 *         updating the lastKnownState
	 */
	@ResponseBody
	@RequestMapping(value = "/deactivate/{id}", method = RequestMethod.PUT)
	public ElroPowerPlug deactivate(@PathVariable("id") Integer id) {
		elroPowerPlugService.setState(id, false);
		return elroPowerPlugService.load(id);
	}

	/**
	 * Retrieve all {@link ElroPowerPlug}s. (TODO available to the requesting
	 * user)
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code GET .../switches}
	 * </pre>
	 * 
	 * @return {@link List} of {@link ElroPowerPlug}
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<ElroPowerPlug> getAll() {
		return elroPowerPlugService.listAll();
	}

	/**
	 * Create a new {@link ElroPowerPlug}.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code POST .../switches}<br/>
	 * and {@link ElroPowerPlug} as {@link RequestBody}
	 * </pre>
	 * 
	 * @param newPlug
	 *            new {@link ElroPowerPlug}
	 * 
	 * @return created {@link ElroPowerPlug}
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public ElroPowerPlug create(@RequestBody ElroPowerPlug newPlug) {
		Integer id = (Integer) elroPowerPlugService.save(newPlug);
		return elroPowerPlugService.load(id);
	}

	/**
	 * Delete {@link ElroPowerPlug} by id from system.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code DELETE .../switches/<exampleId>}
	 * </pre>
	 * 
	 * @param id
	 *            unique {@link ElroPowerPlug} identifier
	 * 
	 * @throws DataNotFoundException
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Integer id)
			throws DataNotFoundException {
		elroPowerPlugService.delete(id);
	}

}
