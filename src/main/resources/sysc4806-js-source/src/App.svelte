<script lang="ts">
 import { onMount } from 'svelte';

 type BuddyInfo = {
	 name: string;
	 phoneNumber: string;
 };

 type AddressBook = {
	 id: number;
	 buddyInfos: Array<BuddyInfo>;
 };

 let selectedAddressBook: number | null = null;
 let addressBooks: Array<AddressBook> = [];
 let buddyInfoFieldName = '';
 let buddyInfoFieldNumber = '';

 async function updateAddressBooks() {
	 addressBooks = await (await fetch(window.location.origin + '/addressbooks')).json();
 }

 onMount(async () => {
	 await updateAddressBooks();
 });

 async function addAddressBook() {
	 await fetch(window.location.origin + '/addressbooks', {
		 method: 'POST',
	 });

	 await updateAddressBooks();
 }

 async function addBuddy() {
	 if (selectedAddressBook === null) {
		 return;
	 }

	 await fetch(window.location.origin + '/addressbooks/add/' + selectedAddressBook, {
		 method: 'POST',
		 headers: {
			'Content-Type': 'application/json',
		 },
		 body: JSON.stringify({
			 name: buddyInfoFieldName,
			 phoneNumber: buddyInfoFieldNumber,
		 }),
	 });

	 await updateAddressBooks();
 }

</script>

<main>
	<h1>Address Books</h1>
	<table>
		{#each addressBooks as addressBook}
			<div>
				<div>
					<input type="checkbox" checked={selectedAddressBook === addressBook.id} />
					<div>Address Book {addressBook.id}</div>
					<button on:click={() => {
									selectedAddressBook = addressBook.id;
									}}>Select Address Book</button>
				</div>
				<div>
					{#each addressBook.buddyInfos as buddyInfo}
						<div>Buddy Name: {buddyInfo.name}</div>
						<div>Buddy Number: {buddyInfo.phoneNumber}</div>
					{/each}
				</div>
			</div>
		{/each}
	</table>

	<div>
	<label for="buddyInfoNameField">Buddy Name</label>
	<input id="buddyInfoNameField" bind:value={buddyInfoFieldName}>

	<label for="buddyInfoNumberField">Buddy Number</label>
	<input id="buddyInfoNumberField" bind:value={buddyInfoFieldNumber}>
	<button on:click={addBuddy}>Add Buddy</button>
	</div>

	<button on:click={addAddressBook}>Add Address Book</button>
</main>

<style>
</style>
