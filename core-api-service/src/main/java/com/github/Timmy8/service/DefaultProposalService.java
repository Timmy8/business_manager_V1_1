package com.github.Timmy8.service;

import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultProposalService implements ProposalService{
    private final ProposalRepository repository;

    @Cacheable(value = "proposals", key = "'allProposals'")
    @Override
    public List<Proposal> findAllProposals() {
        return repository.findAll();
    }

    @Override
    public Optional<Proposal> findProposal(Integer proposalId) {
        return repository.findById(proposalId);
    }

    @CacheEvict(value = "proposals", allEntries = true)
    @Transactional
    @Override
    public Proposal createProposal(String name, String description, BigDecimal price) {
        return repository.save(new Proposal(null, name, description, price, new ArrayList<>()));
    }

    @CacheEvict(value = "proposals", allEntries = true)
    @Transactional
    @Override
    public void updateProposal(Integer proposalId, String name, String description, BigDecimal price) {
        repository.findById(proposalId).ifPresentOrElse(proposal -> {
            proposal.setName(name);
            proposal.setDescription(description);
            proposal.setPrice(price);
        }, () -> {
            throw new NoSuchElementException("{api.proposal.create.errors.proposal_not_found}");
        });
    }

    @CacheEvict(value = "proposals", allEntries = true)
    @Transactional
    @Override
    public void deleteProposal(Integer proposalId) {
        repository.deleteById(proposalId);
    }
}
