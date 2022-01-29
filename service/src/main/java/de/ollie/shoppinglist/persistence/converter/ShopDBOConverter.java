package de.ollie.shoppinglist.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import de.ollie.shoppinglist.core.model.Shop;
import de.ollie.shoppinglist.persistence.entity.ShopDBO;
import de.ollie.shoppinglist.persistence.repository.UserDBORepository;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * A DBO converter for shops.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class ShopDBOConverter implements ToModelConverter<Shop, ShopDBO> {

	private final UserDBORepository userDBORepository;

	public ShopDBO toDBO(Shop model) {
		if (model == null) {
			return null;
		}
		return new ShopDBO()
				.setId(model.getId())
				.setUser((model.getUser() == null ? null : userDBORepository.findById(model.getUser()).orElse(null)))
				.setName(model.getName())
				.setSortOrder(model.getSortOrder());
	}

	@Override
	public Shop toModel(ShopDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Shop()
				.setId(dbo.getId())
				.setUser(dbo.getUser() == null ? null : dbo.getUser().getId())
				.setName(dbo.getName())
				.setSortOrder(dbo.getSortOrder());
	}

	@Override
	public List<Shop> toModel(List<ShopDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}