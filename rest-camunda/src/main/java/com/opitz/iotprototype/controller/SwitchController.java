package com.opitz.iotprototype.controller;

import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.services.ElroPowerPlugService;
import com.opitz.iotprototype.utils.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * Update an {@link ElroPowerPlug}.
     * <p>
     *
     * <pre>
     * <b>REST call example:</b><br/>
     * {@code POST .../switches/{id}}<br/>
     * and {@link ElroPowerPlug} as {@link RequestBody}
     * </pre>
     *
     * @param plug
     *            {@link ElroPowerPlug}
     *
     * @return updated{@link ElroPowerPlug}
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ElroPowerPlug update(@RequestBody ElroPowerPlug plug) {
        Integer id = (Integer) elroPowerPlugService.save(plug);
        return elroPowerPlugService.load(id);
    }

}
